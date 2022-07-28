var selectedBrand = $('#brand-select').val();
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
        }
    })
}
function displayCarItems(carList){
    $('#cars > div').remove();
    $.each(carList,function (i,item){
        $('#cars').append(`<div class="border-0 p-5 col-4">
                                <img src="${item.imageUrl}" class="card-img-top itemImages">
                                <div class="card-body">
                                    <h2 class="cmp-item__title">${item.description}</h2>
                                </div>
                                <div class="d-grid gap-2">
                                    <a class="cmp-item__button mt-2 btn btn-lg text-white fontSize" href="#" role="button">
                                        See details
                                    </a>
                                </div>
                                </div>`)
    })
}