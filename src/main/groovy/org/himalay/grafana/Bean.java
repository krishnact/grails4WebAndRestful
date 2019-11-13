package org.himalay.grafana;

class Bean<T>
{
    private T value_;
    
    public Bean(T value){
        this.value_ = value;
    }
    public T getValue(){
        return value_;
    }
    
    public void setValue(T value){
        this.value_ = value;
    }
}