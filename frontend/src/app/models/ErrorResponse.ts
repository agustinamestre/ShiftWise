export default interface ErrorResponse {
  genericErrorMessage: string | undefined;
  statusCode: number;
  propertyErrors: {
    property: string;
    error: string;
  }[] | undefined;
}

