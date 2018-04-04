package hu.elte.matrix.controller;

import hu.elte.matrix.exception.DimensionException;
import hu.elte.matrix.model.Matrix;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MatrixOperationController {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/api/matrix/add", method = RequestMethod.POST,
            consumes = "application/json")
    public String addMatrices(Map<String, Object> model,
                              @RequestBody Map<String, double[][]> request) {
        Matrix result;
        Matrix firstMatrix = new Matrix(request.get("firstMatrix"));
        Matrix secondMatrix = new Matrix(request.get("secondMatrix"));
        try {
            result = firstMatrix.add(secondMatrix);
        } catch (DimensionException e) {
            e.printStackTrace();
            String errorMessage = env.getProperty("message.operation.dimensionException");
            model.put("errorMessage", errorMessage);
            return "resultError";
        }

        model.put("resultMatrix", result);
        return "resultMatrix";
    }

    @RequestMapping(value = "/api/matrix/subtract", method = RequestMethod.POST,
            consumes = "application/json")
    public String subtractMatrices(Map<String, Object> model,
                                   @RequestBody Map<String, double[][]> request) {
        Matrix result;
        Matrix firstMatrix = new Matrix(request.get("firstMatrix"));
        Matrix secondMatrix = new Matrix(request.get("secondMatrix"));
        try {
            result = firstMatrix.subtract(secondMatrix);
        } catch (DimensionException e) {
            e.printStackTrace();
            String errorMessage = env.getProperty("message.operation.dimensionException");
            model.put("errorMessage", errorMessage);
            return "resultError";
        }
        model.put("resultMatrix", result);
        return "resultMatrix";
    }

    @RequestMapping(value = "/api/matrix/multiply/{constant}", method = RequestMethod.POST,
            consumes = "application/json")
    public String multiplyMatrix(Map<String, Object> model,
                                 @RequestBody Matrix matrix, @PathVariable double constant) {
        Matrix result;
        result = matrix.multiply(constant);

        model.put("resultMatrix", result);
        return "resultMatrix";
    }

    @RequestMapping(value = "/api/matrix/multiply", method = RequestMethod.POST,
            consumes = "application/json")
    public String multiplyMatrices(Map<String, Object> model,
                                   @RequestBody Map<String, double[][]> request) {
        Matrix result;
        Matrix firstMatrix = new Matrix(request.get("firstMatrix"));
        Matrix secondMatrix = new Matrix(request.get("secondMatrix"));
        try {
            result = firstMatrix.multiply(secondMatrix);
        } catch (DimensionException e) {
            e.printStackTrace();
            String errorMessage = env.getProperty("message.operation.dimensionException");
            model.put("errorMessage", errorMessage);
            return "resultError";
        }
        model.put("resultMatrix", result);
        return "resultMatrix";
    }
}
