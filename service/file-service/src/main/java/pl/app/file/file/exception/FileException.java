package pl.app.file.file.exception;

import pl.app.file.file_storage.domain.FileLocation;

public interface FileException {
    class NotFoundFileException extends RuntimeException {
        public NotFoundFileException() {
            super();
        }

        public NotFoundFileException(String message) {
            super(message);
        }

        public static NotFoundFileException fromId(String fileId) {
            return new NotFoundFileException("Not found file with id: " + fileId);
        }
    }

    class NotFoundFileStorageException extends RuntimeException {
        public NotFoundFileStorageException() {
            super();
        }

        public NotFoundFileStorageException(String message) {
            super(message);
        }

        public static NotFoundFileStorageException fromFileLocation(FileLocation fileLocation) {
            return new NotFoundFileStorageException("Not found file:" + fileLocation.getName() + " in directory: " + fileLocation.getDirectory());
        }
    }
}
