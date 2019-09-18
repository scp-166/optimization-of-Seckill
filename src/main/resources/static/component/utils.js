class Utils{
    constructor(){
        this.instance = axios.create({});
        this.instance.defaults
            // .post  // 可以指定特定发送方式
            .headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
    }
    getInstance(){
        return this.instance;
    }
}

window.utils = new Utils();

