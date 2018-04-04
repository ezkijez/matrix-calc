const URL = '/api/matrix/';
const CONTENT_TYPE = 'application/json';

$(document).ready(() => {
    getInitialView();
    bindEventHandlersForInputs();
});

function processMatrixInput(firstMatrixInputValue, selectedOptionValue) {
    hideErrorMessage();
    hideMissingOptionErrorMessage();

    let matrix = [[]];
    const matrixInputValue = firstMatrixInputValue;
    const option = selectedOptionValue;

    if (option !== null) {
        const allElementIsNumber = checkIfAllElementIsNumber(matrixInputValue);

        let validMatrix;
        if (allElementIsNumber) {
            validMatrix = processLines(matrixInputValue, matrix);
        }

        if (validMatrix) {
            sendMatrixToServer(option, matrix);
        } else {
            showErrorMessage();
        }
    } else {
        showErrorMessage();
    }
}

function processMatricesInput(firstMatrixInputValue, secondMatrixInputValue, selectedOptionValue) {
    hideErrorMessage();
    hideMissingOptionErrorMessage();

    let firstMatrix = [[]];
    let secondMatrix = [[]];
    const firstMatrixValue = firstMatrixInputValue;
    const secondMatrixValue = secondMatrixInputValue;
    const operation = selectedOptionValue;

    if (operation !== null) {
        if (operation === 'multiply' && !inputIsMatrix(secondMatrixValue)) {
            const allElementIsNumberInFirstMatrix = checkIfAllElementIsNumber(firstMatrixValue);
            const validNumber = isNumeric(secondMatrixValue);

            let validMatrix = false;
            if (allElementIsNumberInFirstMatrix && validNumber) {
                validMatrix = processLines(firstMatrixValue, firstMatrix);
            }

            if (validMatrix) {
                sendMatrixAndConstantToServer(operation, firstMatrix, secondMatrixValue);
            } else {
                showErrorMessage();
            }
        } else {
            const allElementIsNumberInFirstMatrix = checkIfAllElementIsNumber(firstMatrixValue);
            const allElementIsNumberInSecondtMatrix = checkIfAllElementIsNumber(secondMatrixValue);

            let validFirstMatrix;
            let secondFirstMatrix;
            if (allElementIsNumberInFirstMatrix && allElementIsNumberInSecondtMatrix) {
                validFirstMatrix = processLines(firstMatrixValue, firstMatrix);
                secondFirstMatrix = processLines(secondMatrixValue, secondMatrix);
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
        showMissingOptionErrorMessage();
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

function inputIsMatrix(value) {
    let lines = value.split('\n');
    if (lines.length === 1) {
        let elements = lines[0].split(' ');
        return elements.length !== 1;
    } else return lines.length > 1;
}

function isSelectedOptionIsOperation(option) {
    return option === 'multiply' || option === 'add' || option === 'subtract';
}

function isSelectedOptionIsAttribution(option) {
    return option === 'determinant' || option === 'inverse' || option === 'rank';
}

function matricesHaveSameRowAndColNum(firstMatrix, secondMatrix) {
    return firstMatrix.length === secondMatrix.length && firstMatrix[0].length === secondMatrix[0].length;
}

function sendMatrixToServer(option, matrix) {
    $.ajax({
        type: 'POST',
        url: URL + option,
        contentType: CONTENT_TYPE,
        data: JSON.stringify({
            matrix: matrix
        }),
        success: (response) => {
            $('.js-matrix-result').html(response);
        }
    });
}

function sendMatrixAndConstantToServer(operation, matrix, constant) {
    $.ajax({
        type: 'POST',
        url: URL + operation + '/' + constant,
        contentType: CONTENT_TYPE,
        data: JSON.stringify({
            matrix: matrix
        }),
        success: (response) => {
            $('.js-matrix-result').html(response);
        }
    });
}

function sendMatricesToServer(operation, firstMatrix, secondMatrix) {
    $.ajax({
        type: 'POST',
        url: URL + operation,
        contentType: CONTENT_TYPE,
        data: JSON.stringify({
            firstMatrix: firstMatrix,
            secondMatrix: secondMatrix
        }),
        success: (response) => {
            $('.js-matrix-result').html(response);
        }
    });
}

function isNumeric(num) {
    return !isNaN(num);
}

function bindEventHandlerForSendButton() {
    $('#send').on('click', () => {
        $('.js-matrix-result').html('');

        const firstMatrixInputValue = $('.js-matrix-input-first').val();
        const secondMatrixInputValue = $('.js-matrix-input-second').val();
        const selectedValue = $('.js-matrix-options').val();

        if (selectedValue !== null) {
            if (secondMatrixInputValue !== '' && isSelectedOptionIsOperation(selectedValue)) {
                processMatricesInput(firstMatrixInputValue, secondMatrixInputValue, selectedValue);
            } else if (firstMatrixInputValue !== '' && isSelectedOptionIsAttribution(selectedValue)) {
                processMatrixInput(firstMatrixInputValue, selectedValue);
            } else {
                showErrorMessage();
            }
        } else {
            showMissingOptionErrorMessage();
        }
    });
}

function bindEventHandlerForSelector() {
    $('.js-matrix-options').on('change', (event) => {
        hideMissingOptionErrorMessage();
        const selectedValue = event.target.value;
        if (isSelectedOptionIsOperation(selectedValue)) {
            showSecondMatrixInput();
        } else {
            hideSecondMatrixInput();
        }
    });
}

function bindEventHandlerForMatricesInput() {
    $('.js-matrix-input-first').on('change keyup paste', () => {
        hideErrorMessage();
    });

    $('.js-matrix-input-second').on('change keyup paste', () => {
        hideErrorMessage();
    });
}

function bindEventHandlersForInputs() {
    bindEventHandlerForSendButton();
    bindEventHandlerForSelector();
    bindEventHandlerForMatricesInput();
}

function getInitialView() {
    hideErrorMessage();
    hideSecondMatrixInput();
    hideMissingOptionErrorMessage();
}

function showMissingOptionErrorMessage() {
    $('.js-matrix-missing').show();
}

function hideMissingOptionErrorMessage() {
    $('.js-matrix-missing').hide();
}

function showErrorMessage() {
    $('.js-matrix-invalid').show();
}

function hideErrorMessage() {
    $('.js-matrix-invalid').hide();
}

function showSecondMatrixInput() {
    $('.js-matrix-input-second').show();
}

function hideSecondMatrixInput() {
    $('.js-matrix-input-second').hide();
}