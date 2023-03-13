import {ApiClientContextType} from "../context/ApiClientContext";

export default class UserService{
    private apiClientContext: ApiClientContextType;

    constructor(apiClientContext: ApiClientContextType) {
        this.apiClientContext = apiClientContext;
    }

    findAll(){
        return null;
    }

    save(){
        console.log("User service is saving...", this)
        return null;
    }
}