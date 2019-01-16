package wzl.com.wproject.bean;

public class FirstCate {

    /**
     * id : 1001002
     * name : 女装
     */

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FirstCate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
