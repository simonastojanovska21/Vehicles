mapCanvas = document.getElementById('myMap');
console.log(mapCanvas)
var lat = mapCanvas.dataset.lat
var lon = mapCanvas.dataset.lon;

console.log(lat);
console.log(lon)

var map = L.map(mapCanvas).setView([lat,lon], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '© OpenStreetMap'
}).addTo(map);



var marker = L.marker([lat,lon]).addTo(map);