var carId = localStorage.getItem("carId");

if (carId !== null){
    console.log(carId);
    $.ajax({
        type: 'GET',
        url : '/bin/carDetails?carId='+carId,
        success: function (data){

            // var car = data;
            console.log(data);
            console.log(data.brandId)
            $('#carDetails').append(`<div class="col-6">
                            <img src="${data.imageUrl}" class="img-fluid" alt="car image">
                        </div>
                        <div class="col-6">
                            <h1 class="cmp-car__title">${data.brandName} - ${data.carModelName} </h1>
                            <br/>
                            <h3>Technical details</h3>
                            <hr/>
                            <h4>Year: ${data.year}</h4>
                            <h4>Kilometers: ${data.kilometers}</h4>
                            <h4>Transmission: ${data.transmission}</h4>
                            <h4>Body style: ${data.bodyStyle}</h4>

                        </div>`)


        }
    })
}

