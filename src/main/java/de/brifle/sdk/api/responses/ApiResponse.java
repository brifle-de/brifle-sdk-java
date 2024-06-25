package de.brifle.sdk.api.responses;

public class ApiResponse<DataType> {

    private DataType data;
    private ErrorResponse error;

    public DataType getData() {
        return data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isSuccess() {
        return data != null;
    }

    /**
     * Creates a success response
     * @param data the data
     * @return the response
     * @param <DataType> the data type
     */
    public static <DataType> ApiResponse<DataType> success(DataType data) {
        ApiResponse<DataType> response = new ApiResponse<>();
        response.data = data;
        return response;
    }

    /**
     * Creates an error response
     * @param error the error
     * @return the response
     * @param <DataType> the data type
     */
    public static <DataType> ApiResponse<DataType> error(ErrorResponse error) {
        ApiResponse<DataType> response = new ApiResponse<>();
        response.error = error;
        return response;
    }

}
