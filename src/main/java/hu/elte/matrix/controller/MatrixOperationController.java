package hu.elte.matrix.controller;

import hu.elte.matrix.exception.DimensionException;
import hu.elte.matrix.model.Matrix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MatrixOperationController {

    private final String pageTitle = "Mátrix műveletek";

    @RequestMapping(value = "/r/matrix/operation", method = RequestMethod.GET)
    public String matrixOperationPage(Map<String, Object> model) {
        model.put("pageTitle", pageTitle);
        return "operation";
    }

    @RequestMapping(value = "/api/matrix/add", method = RequestMethod.POST, consumes="application/json")
    public String addMatrices(Map<String, Object> model, @RequestBody Map<String, double[][]> request) {
        Matrix result = null;
        Matrix firstMatrix = new Matrix(request.get("firstMatrix"));
        Matrix secondMatrix = new Matrix(request.get("secondMatrix"));
        try {
            result = firstMatrix.add(secondMatrix);
        } catch (DimensionException e) {
            e.printStackTrace();
        }

        model.put("pageTitle", "Eredmény");
        model.put("firstMatrix", firstMatrix);
        model.put("secondMatrix", secondMatrix);
        model.put("resultMatrix", result);
        return "addsubtract";
    }

    @RequestMapping(value = "/api/matrix/subtract", method = RequestMethod.POST, consumes="application/json")
    public String subtractMatrices(Map<String, Object> model, @RequestBody Map<String, double[][]> request) {
        Matrix result = null;
        Matrix firstMatrix = new Matrix(request.get("firstMatrix"));
        Matrix secondMatrix = new Matrix(request.get("secondMatrix"));
        try {
            result = firstMatrix.subtract(secondMatrix);
        } catch (DimensionException e) {
            e.printStackTrace();
        }

        model.put("pageTitle", "Eredmény");
        model.put("firstMatrix", firstMatrix);
        model.put("secondMatrix", secondMatrix);
        model.put("resultMatrix", result);
        return "addsubtract";
    }

    @RequestMapping(value = "/api/matrix/multiply/{constant}", method = RequestMethod.POST, consumes="application/json")
    public String multiplyMatrix(Map<String, Object> model, @RequestBody Matrix matrix, @PathVariable Long constant) {
        Matrix result;

        result = matrix.multiply(constant);

        model.put("pageTitle", "Eredmény");
        model.put("matrix", matrix);
        model.put("resultMatrix", result);
        model.put("constant", constant);

        return "multiply";
    }
}
