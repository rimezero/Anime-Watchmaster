package animeApp.model;

/**
 * Created by admin on 7/4/2016.
 */
public class SeasonsSortModel implements Comparable{
    private String season;
    private int year;
    private int order;

    public SeasonsSortModel(String season, int year) {
        this.season = season;
        this.year = year;
        setOrder();
    }

    private void setOrder(){
        switch (season){
            case "Fall":
                this.order=0;
                break;
            case "Summer":
                this.order=1;
                break;
            case "Spring":
                this.order=2;
                break;
            case "Winter":
                this.order=3;
                break;
            default:
                this.order=4;
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(Object another) {
        if(this.year>((SeasonsSortModel)another).getYear()){
            return -1;
        }else if(this.year<((SeasonsSortModel)another).getYear()){
            return 1;
        }else{
            if(this.order<((SeasonsSortModel)another).getOrder()){
                return -1;
            }else if(this.order>((SeasonsSortModel)another).getOrder()){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString(){
        return this.season+" "+this.year;
    }

}
