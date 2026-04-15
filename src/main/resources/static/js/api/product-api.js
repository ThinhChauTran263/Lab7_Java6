import {httpClient} from "../http-client.js";

export const productApi = {
    async getAll() {
        const response = await httpClient.get("/products");
        return response.data;
    },
    async create(payload) {
        const response = await httpClient.post("/products", payload);
        return response.data;
    },
    async update(id, payload) {
        const response = await httpClient.put(`/products/${id}`, payload);
        return response.data;
    },
    async remove(id) {
        await httpClient.delete(`/products/${id}`);
    }
};

