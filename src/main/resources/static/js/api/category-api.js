import {httpClient} from "../http-client.js";

export const categoryApi = {
    async getAll() {
        const response = await httpClient.get("/categories");
        return response.data;
    },
    async create(payload) {
        const response = await httpClient.post("/categories", payload);
        return response.data;
    },
    async update(id, payload) {
        const response = await httpClient.put(`/categories/${id}`, payload);
        return response.data;
    },
    async remove(id) {
        await httpClient.delete(`/categories/${id}`);
    }
};

