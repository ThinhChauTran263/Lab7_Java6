import {httpClient} from "../http-client.js";

export const userApi = {
    async getAll() {
        const response = await httpClient.get("/users");
        return response.data;
    },
    async create(payload) {
        const response = await httpClient.post("/users", payload);
        return response.data;
    },
    async update(username, payload) {
        const response = await httpClient.put(`/users/${username}`, payload);
        return response.data;
    },
    async remove(username) {
        await httpClient.delete(`/users/${username}`);
    }
};

