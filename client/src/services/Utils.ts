export function getApiServerUrl(){
    return import.meta.env.VITE_API_SERVER_URL || 'http://127.0.0.1:8080';
    /*if(import.meta.env.DEV) return import.meta.env.VITE_API_SERVER_URL || 'http://127.0.0.1:8080';

    return getTag("API_SERVER_URL");*/
}

export function getEnv(){
    return getTag("APP_ENV");
}

function getTag(tagName: string){
    const element = document.querySelector(`meta[property=${tagName}]`);
    if(!element) return '';

    //@ts-ignore
    return element.content;
}