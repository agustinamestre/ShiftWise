interface BackendError {
  errorCode: string;
  errorMessage: string;
}

interface BackendErrorResponse {
  error: BackendError[];
}

import ErrorResponse from './ErrorResponse';

export function mapBackendErrorToErrorResponse(
  backendError: BackendErrorResponse,
  statusCode: number
): ErrorResponse {
  return {
    genericErrorMessage: backendError.error?.[0]?.errorMessage,
    statusCode,
    propertyErrors: backendError.error?.map((e) => ({
      property: e.errorCode,
      error: e.errorMessage,
    })),
  };
}
