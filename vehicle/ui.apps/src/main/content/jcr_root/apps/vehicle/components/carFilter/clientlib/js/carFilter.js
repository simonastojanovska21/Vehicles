var brands ;
var years = [2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022];

$.each(years, function (i, item) {
    $('#year-select').append($('<option',{
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
console.log(brands)


$('#brand-select').on('change', function() {
    var tmp = $('#brand-select').find(":selected").text();
    console.log(tmp)
    if(tmp === "All"){
        $('#model-select').prop('disabled', 'disabled');
    }
    else {
        $('#model-select').prop('disabled', false);
    }
});


