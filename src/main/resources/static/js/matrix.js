$(document).ready(() => {
    hideErrorMessage();
    $('#send').on('click', () => {
        processMatrixInput();
    });

    $('#sendMatrices').on('click', () => {
        processMatricesInput();
    });

    $('.js-matrix-operations').on('click', () => {
        window.location.replace('/r/matrix/operation');
    });
});

function processMatrixInput() {
    hideErrorMessage();
    hideSelectAttributionErrorMessage();
    let matrix = [[]];
    const matrixInputValue = $('.js-matrix-input').val();
    const attribution = $('.js-matrix-attribution').val();

    if (attribution !== null) {
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
    } else {
        showErrorMessage();
    }


}

function processMatricesInput() {
    hideErrorMessage();
    hideSelectAttributionErrorMessage();

    let firstMatrix = [[]];
    let secondMatrix = [[]];
    const firstMatrixInputValue = $('.js-matrix-input-first').val();
    const secondMatrixInputValue = $('.js-matrix-input-second').val();
    const operation = $('.js-matrix-attribution').val();

    if (operation !== undefined) {
        if (operation === 'multiply') {
            const allElementIsNumberInFirstMatrix = checkIfAllElementIsNumber(firstMatrixInputValue);
            const validNumber = isNumeric(secondMatrixInputValue);

            let validMatrix;
            if (allElementIsNumberInFirstMatrix && validNumber) {
                validMatrix = processLines(firstMatrixInputValue, firstMatrix);
            }

            if (validMatrix) {
                sendMatrixAndConstantToServer(operation, firstMatrix, secondMatrixInputValue);
            } else {
                showErrorMessage();
            }
        } else {
            const allElementIsNumberInFirstMatrix = checkIfAllElementIsNumber(firstMatrixInputValue);
            const allElementIsNumberInSecondtMatrix = checkIfAllElementIsNumber(secondMatrixInputValue);

            let validFirstMatrix;
            let secondFirstMatrix;
            if (allElementIsNumberInFirstMatrix && allElementIsNumberInSecondtMatrix) {
                validFirstMatrix = processLines(firstMatrixInputValue, firstMatrix);
                secondFirstMatrix = processLines(secondMatrixInputValue, secondMatrix);
            }

            if (validFirstMatrix && secondFirstMatrix) {
                if (matricesHaveSameRowAndColNum(firstMatrix, secondMatrix)) {
                    sendMatricesToServer(operation, firstMatrix, secondMatrix);
                } else {
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }
        }

    } else {
        showMissingErrorMessage();
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

function matricesHaveSameRowAndColNum(firstMatrix, secondMatrix) {
    return firstMatrix.length === secondMatrix.length && firstMatrix[0].length === secondMatrix[0].length;
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

function sendMatrixAndConstantToServer(operation, matrix, constant) {
    $.ajax({
        type: 'POST',
        url: '/api/matrix/' + operation + '/' + constant,
        contentType: 'application/json',
        data: JSON.stringify({
            matrix: matrix
        }),
        success: (response) => {
            $('html').html(response);
        }
    });
}

function sendMatricesToServer(operation, firstMatrix, secondMatrix) {
    $.ajax({
        type: 'POST',
        url: '/api/matrix/' + operation,
        contentType: 'application/json',
        data: JSON.stringify({
            firstMatrix: firstMatrix,
            secondMatrix: secondMatrix
        }),
        success: (response) => {
            $('html').html(response);
        }
    });
}

function isNumeric(num) {
    return !isNaN(num);
}

function showMissingErrorMessage() {
    $('.js-matrix-missing')[0].style.display = 'none';
}

function hideSelectAttributionErrorMessage() {
    $('.js-matrix-missing')[0].style.display = 'block';
}

function showErrorMessage() {
    $('.js-matrix-invalid')[0].style.display = 'block';
}

function hideErrorMessage() {
    $('.js-matrix-invalid')[0].style.display = 'none';
}