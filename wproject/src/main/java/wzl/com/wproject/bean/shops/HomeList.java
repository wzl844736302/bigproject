package wzl.com.wproject.bean.shops;

import java.util.List;

public class HomeList {
    /**
     * commodityList : [{"commodityId":32,"commodityName":"唐狮女鞋冬季女鞋休闲鞋子女士女鞋百搭帆布鞋女士休闲鞋子女款板鞋休闲女鞋帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/1/1.jpg","price":88,"saleNum":0},{"commodityId":24,"commodityName":"百搭小白鞋 女款 时尚舒适板鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/bx/7/1.jpg","price":149,"saleNum":0}]
     * id : 1003
     * name : 魔力时尚
     */

    private int id;
    private String name;
    private List<Commodity> commodityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Commodity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

}
