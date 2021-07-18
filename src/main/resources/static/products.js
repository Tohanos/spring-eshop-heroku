var stomp = null;

// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connect() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/products', function (product) {
            renderItem(product);
        });
        stomp.subscribe('/topic/bucket', function (bucket) {
            renderBucket(bucket);
        });
        stomp.subscribe('/topic/weather', function (weather) {
            renderWeather(weather);
        });
        stomp.send("/app/menu", {}, {});
    });
}

// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendContent(); });
});

// $(function () {
//     $("toBucket").on('click', function (e) {
//          e.preventDefault();
//     });
//     $( "#toBucket" ).click(function() { sendToBucket(); });
// });

// отправка сообщения на сервер
function sendContent() {
    stomp.send("/app/products", {}, JSON.stringify({
        'title': $("#title").val(),
        'price': $("#price").val()
    }));
}

function sendToBucket(id) {
    stomp.send("/app/bucket", {}, JSON.stringify(id));
}

// рендер сообщения, полученного от сервера
function renderItem(productJson) {
    var product = JSON.parse(productJson.body);
    $("#table").append("<tr>" +
        "<td>" + product.title + "</td>" +
        "<td>" + product.price + "</td>" +
        "<td><button id='toBucket' value=" + product.id + " name='Add to bucket'" +
        "type='button' onclick='sendToBucket(value)'>Add to bucket</button></td>" +
        "</tr>");
}

function renderBucket(bucketJson) {
    var bucket = JSON.parse(bucketJson.body);
    // $("#bucketSum").replaceWith(bucket.sum);
    document.getElementById("bucketSum").innerText = bucket.sum;
}

function renderWeather(weatherJson) {
    var weather = JSON.parse(weatherJson.body);
    // $("#weather-temp").innerHTML = weather.main.temp;
    document.getElementById("weather-temp").innerText = weather.main.temp;
}