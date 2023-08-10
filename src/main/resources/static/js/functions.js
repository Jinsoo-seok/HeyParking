function createMarkerImage(color) {
    var image = null;
    if (color === 'green') {
        image = '/green.png';
    } else if (color === 'orange') {
        image = '/orange.png';
    } else if (color === 'red') {
        image = '/red.png';
    } else {
        image = '/parking.png';
    }
    return new kakao.maps.MarkerImage(image, new kakao.maps.Size(10, 10));
}

function createInfoWindowContent(data) {
    return '<div class="info-container">' +
        '    <div class="info-item">' +
        '        <span class="info-label">Name:</span> <span class="info-value">' + data.parkingName + '</span>' +
        '    </div>' +
        '    <div class="info-item">' +
        '        <span class="info-label">Lat:</span> <span class="info-value">' + data.parkingLat + '</span>' +
        '    </div>' +
        '    <div class="info-item">' +
        '        <span class="info-label">Lon:</span> <span class="info-value">' + data.parkingLon + '</span>' +
        '    </div>' +
        '    <div class="info-item">' +
        '        <span class="info-label">Status:</span> <span class="info-value">' + data.parkingColor + '</span>' +
        '    </div>' +
        '</div>';
}

function extractValue(content, label) {
    var startIndex = content.indexOf(label) + label.length;
    var startTag = '<span class="info-value">';
    var endTag = '</span>';
    var startIndexValue = content.indexOf(startTag, startIndex) + startTag.length;
    var endIndexValue = content.indexOf(endTag, startIndexValue);

    var extractedValue = content.substring(startIndexValue, endIndexValue).trim();

    return extractedValue;
}