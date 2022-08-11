$.ajax({
    type : 'GET',
    url : "/bin/brands",
    success: function (data){
        brands = data
        $.each(brands, function (i, item) {
            $('#carBrand').append($('<option>', {
                value: item.brandId,
                text : item.brandName
            }));
        });
    }
})

$.ajax({
    type : 'GET',
    url : '/bin/addNewCar',
    success: function (data){

        console.log(JSON.parse(data.transmission))
        console.log(JSON.parse(data.bodyStyle))
        $.each( JSON.parse(data.transmission),function (i,item){
            $('#transmission').append(`<div class="form-check form-check-inline me-3" >
                                    <input class="form-check-input" type="radio" name="transmission" value="${item}">
                                    <label class="form-check-label">${item}</label>
                               </div>`)
        })

        $.each(JSON.parse(data.bodyStyle),function (i,item){
            $('#bodyStyle').append(`<div class="form-check form-check-inline me-3" >
                                    <input class="form-check-input" type="radio" name="bodyStyle" value="${item}">
                                    <label class="form-check-label">${item}</label>
                               </div>`)
        })

    }
})

$('#carBrand').on('change', function() {
    var selectedBrand = $('#carBrand').val();
    if(selectedBrand === "Select"){
        $('#carModel').val("Select")
        $('#carModel').prop('disabled', 'disabled');
    }
    else {
        console.log("value changed")
        $('#carModel').prop('disabled', false);
        $('#carModel').find('option').not(':first').remove();
        $.ajax({
            type : 'GET',
            url : "/bin/carModels?brandId="+selectedBrand,
            success: function (data){
                var carModels = data

                $.each(carModels, function (i, item) {
                    $('#carModel').append($('<option>', {
                        value: item.carModelId,
                        text : item.modelName
                    }));
                });
            }
        })
    }
});


$("#submitNewCar").on('click',function(){
    var sentObject = {
            brandId: $('#carBrand').val(),
            brandName: $('#carBrand option:selected').text(),
            carModelId : $('#carModel').val(),
            carModelName : $('#carModel option:selected').text(),
            imageUrl : $('#imageUrl').val(),
            year : $('#year').val(),
            kilometers : $('#kilometers').val(),
            transmission : $('input[name=transmission]:checked').val(),
            bodyStyle : $('input[name=bodyStyle]:checked').val()
        };
    $.ajax({
        type : 'POST',
        url : '/bin/addNewCar',
        data: JSON.stringify(sentObject),
        dataType: 'json',
        contentType: 'application/json'
    })

});

