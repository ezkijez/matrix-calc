$(document).ready(() => {
    hideErrorMessage();
    $('#send').on('click', () => {
        processMatrixInput()
    });
});

function processMatrixInput() {
    hideErrorMessage();
    let matrix = [[]];
    const matrixInputValue = $('.js-matrix-input')[0].value;
    const attribution = $('.js-matrix-attribution')[0].value;

    const allElementIsNumber = checkIfAllElementIsNumber(matrixInputValue);

    let validMatrix;
    if (allElementIsNumber) {
        validMatrix = processLines(matrixInputValue, matrix);
    }

    if (validMatrix) {
        sendMatrixToServer(attribution, matrix);
    } else {
        showErrorMessage();
    }
}

function processLines(value, matrix) {
    let lines = value.split('\n');
    let firstLine = true;
    let numberOfElements = 0;
    let validMatrix = true;

    let i = 0;
    let j = 0;
    lines.map(line => {
        let rowElements = line.split(' ');
        if (firstLine) {
            numberOfElements = rowElements.length;
            i = addToMatrix(i, j, rowElements, matrix);
            firstLine = false;
        } else {
            if (checkNumberOfElements(numberOfElements, rowElements)) {
                i = addToMatrix(i, j, rowElements, matrix);
            } else {
                validMatrix = false;
            }
        }
    });

    return validMatrix;
}

function checkNumberOfElements(numberOfElements, rowElements) {
    return rowElements.length === numberOfElements;
}

function addToMatrix(row, col, elements, matrix) {
    col = 0;
    let rowElements = [];
    elements.map(element => {
        rowElements[col] = element;
        col++;
    });
    matrix[row] = rowElements;
    row++;
    return row;
}

function checkIfAllElementIsNumber(value) {
    let validMatrix = true;
    let lines = value.split('\n');
    lines.map(line => {
        let rowElements = line.split(' ');
        rowElements.map(element => {
            if (!isNumeric(element)) {
                validMatrix = false;
            }
        });
    });


    return validMatrix;
}

function sendMatrixToServer(attribution, matrix) {
    $.ajax({
        type: 'POST',
        url: '/api/matrix/' + attribution,
        contentType: 'application/json',
        data: JSON.stringify({
            matrix: matrix
        }),
        success: (response) => {
            $('html').html(response);
        }
    });
}

function isNumeric(num) {
    return !isNaN(num);
}

function hideErrorMessage() {
    $('.js-matrix-invalid')[0].style.display = 'none';
}

function showErrorMessage() {
    $('.js-matrix-invalid')[0].style.display = 'block';
}