import md5 from 'js-md5';
const key = 'acc0515f34056ad8e32238910c6c3100';
export const encry = {
    create(content) {
        return md5(md5(key + content + key));
    }
}