var selectedBrand = jQuery('#brand-select').val();
var selectedYear = $('#year-select').val();
var selectedModel = $('#model-select').val()
makeAjaxCall(selectedBrand,selectedModel,selectedYear)


$('#brand-select').on('change', function() {
    var selectedBrand = $('#brand-select').val();
    var selectedYear = $('#year-select').val()
    console.log(selectedBrand)
    console.log(selectedYear)
    if(selectedBrand === "All"){
        $('#model-select').val("All")
        $('#model-select').prop('disabled', 'disabled');
    }
    else {
        console.log("value changed")
        $('#model-select').prop('disabled', false);
        $('#model-select ').find('option').not(':first').remove();
        $.ajax({
            type : 'GET',
            url : "/bin/carModels?brandId="+selectedBrand,
            success: function (data){
                var carModels = data

                $.each(carModels, function (i, item) {
                    $('#model-select').append($('<option>', {
                        value: item.carModelId,
                        text : item.modelName
                    }));
                });
                console.log(carModels)
            }
        })
    }
    var selectedModel = $('#model-select').val()
    makeAjaxCall(selectedBrand,selectedModel,selectedYear)
});

$('#model-select').on('change',function (){
    var selectedBrand = $('#brand-select').val();
    var selectedYear = $('#year-select').val();
    var selectedModel = $('#model-select').val()
    makeAjaxCall(selectedBrand,selectedModel,selectedYear)
})

$('#year-select').on('change',function (){
    var selectedBrand = $('#brand-select').val();
    var selectedYear = $('#year-select').val();
    var selectedModel = $('#model-select').val()
    makeAjaxCall(selectedBrand,selectedModel,selectedYear)
})

function makeAjaxCall(brand, model, year){
    $.ajax({
        type: 'GET',
        url: '/bin/cars?brandId='+brand+'&carModelId='+model+'&year='+year,
        success: function (data){
            displayCarItems(data);
            //buttons();
        }
    })
}
function displayCarItems(carList){
    $('#cars > div').remove();

    $.each(carList,function (i,item){
        $('#cars').append(`<div class="col-3">
                                <div class="shadow p-3 mb-5 bg-body rounded border-0 card cmp-item">
                                <img src="${item.imageUrl}" class="card-img-top itemImages">
                                <div class="card-body">
                                    <h4 class="cmp-item__title text-center ">${item.description}</h4>
                                </div>
                                <div class="d-grid gap-2 ">
                                    <button name="detailsButton" onclick="buttons(this)" data-carid="${item.carId}" class="cmp-item__button mt-2 btn btn-lg text-white fontSize" 
                                        role="button">
                                        See details
                                    </button>
                                </div>
                                </div>
</div>`)
    })
}
function buttons(el){
    console.log('clicked')
    var carId = $(el).attr("data-carid");
    console.log(carId)
    localStorage.setItem("carId",carId);
    window.location.href="/content/vehicle/us/en/car-details.html";
    //displayCarDetails(carId);
    // $('button[name="detailsButton"]').on('click',function (){
    //     console.log('clicked')
    //     var carId = this.dataset.carid;
    //     console.log(carId)
    // })
}