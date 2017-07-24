var map;
function initialize() {
    // set some default map details, initial center point, zoom and style
    var mapOptions = {
      center: new google.maps.LatLng(37.7749,-122.4194),
      zoom: 11,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    // create the map and reference the div#map container
    map = new google.maps.Map(document.getElementById("map"), mapOptions);
}

// binds a map marker and infoWindow together on click
var bindInfoWindow = function(marker, map, infowindow, html) {
    google.maps.event.addListener(marker, 'click', function() {
	    infowindow.setContent(html);
	    infowindow.open(map, marker);
	});
}

$(document).ready(function () {

    $("#location-search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();
    });

});

function fire_ajax_submit() {
    $("#bth-location-search").prop("disabled", true);
    var lat = 37.0;
    var long = -122.0;
    var geocoder =  new google.maps.Geocoder();
    // when page is ready, initialize the map!
    initialize();
    google.maps.event.addDomListener(window, 'load', initialize);
	var infowindow =  new google.maps.InfoWindow({
		content: ''
	});
    geocoder.geocode( { 'address': $("#location").val()}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            console.log("location : " + results[0].geometry.location.lat() + " " +results[0].geometry.location.lng());
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/demo/foodtrucks?lat=" + results[0].geometry.location.lat() + "&long=" + results[0].geometry.location.lng(),
                dataType: "json",
                cache: false,
                timeout: 600000,
                success: function (data) {
                    $("#bth-location-search").prop("disabled", false);
                    $("#result").empty();
                    console.log("SUCCESS!");
                    $('#result').append('<div class = "row">');
                    $('#result').append('<div class="list-group">');
                    var result = '';
                    // put marker for user's unput location on map
                    var marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location,
                        title: "currentLocation",
                        icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
                    });
                    bindInfoWindow(marker, map, infowindow, "Your input location");
                    $.each(data, function(idx, obj) {
                                    var truckDetail = $.parseJSON(obj.truckDetail);
                                    console.log(obj);
                                    result = $('<div class = "well">');
                                    result.append('<div class = "col-sm-6 col-md-12">');
                                    result.append('<h3>' + truckDetail.applicant + '</h3>');
                                    result.append('<p>' + "Location: " + truckDetail.address + '</p>');
                                    result.append('<p>' + truckDetail.foodItems + '</p>');
                                    result.append('<tr>');
                                    result.append('<td><span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>' + " around " + obj.distance.toFixed(1) + '</td>');
                                    result.append('<td colspan="1"></td>');
                                    result.append('<td><span class="glyphicon glyphicon-time" aria-hidden="true"></span>' + " " + truckDetail.dayshours + '</td>');
                                    result.append('</tr>');
                                    result.append("</div>")
                                    result.append("</div>")
                                    $('#result').append(result);

                                    var myLatLng = {lat: truckDetail.latitude, lng: truckDetail.longitude};
                                    // put selected foodtruck's marker on map
                                    var marker = new google.maps.Marker({
                                        map: map,
                                        position: myLatLng,
                                        title: truckDetail.applicant
                                    });
                                    // infowindow content
                                    var contentString = result[0].innerHTML;
                                    bindInfoWindow(marker, map, infowindow, contentString);
                    });
                    $("#result").append('</div>');
                    $("#result").append('</div>');
                },
                error: function (e) {

                    var json = "<h4>Ajax Response</h4><pre>"
                        + e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);
                    $("#bth-location-search").prop("disabled", false);
                }
            });
        } else {
            $("#result").html('<div class="alert alert-danger" role="alert">' + "Something got wrong: " + status + '</div>');
        }
    });
}