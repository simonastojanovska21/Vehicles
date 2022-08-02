var brands ;
var years = [2018,2019,2020,2021,2022];

$.each(years, function (i, item) {
    $('#year-select').append($('<option>',{
        value: item,
        text :item
    }));
})

$.ajax({
    type : 'GET',
    url : "/bin/brands",
    success: function (data){
        brands = data
        $.each(brands, function (i, item) {
            $('#brand-select').append($('<option>', {
                value: item.brandId,
                text : item.brandName
            }));
            console.log(item);
        });
    }
})































// use(['getData.js'], function (getData) {
//     var years = [2018,2019,2020,2021,2022];
//     var filters = {};
//     filters.years = years;
//     filters.brands=makeAjaxCall();
//     return filters;
// });
//
