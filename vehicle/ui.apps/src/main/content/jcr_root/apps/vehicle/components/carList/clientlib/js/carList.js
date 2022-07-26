var carList=[];

$.ajax({
    type: 'GET',
    url: '/bin/cars',
    success: function (data){
        carList = data;
        $.each(carList,function (i,item){
            $('#cars').append(`<div class="border-0 p-5 col-4">
                                <img src="${item.imageUrl}" class="card-img-top itemImages">
                                <div class="card-body">
                                    <h2 class="cmp-item__title">${item.brandId} - ${item.carModelId}</h2>
                                </div>
                                <div class="d-grid gap-2">
                                    <a class="cmp-item__button mt-2 btn btn-lg text-white fontSize" href="#" role="button">
                                        See details
                                    </a>
                                </div>
                                </div>`)
        })

        console.log(carList)
    }
})