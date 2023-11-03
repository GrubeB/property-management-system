package pl.app.common.core.service.dto;

public class DtoRequest implements Dto {
    private String className;

    protected DtoRequest(String className) {
        this.className = className;
    }

    public static DtoRequest of(String className) {
        return new DtoRequest(className);
    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }
}
