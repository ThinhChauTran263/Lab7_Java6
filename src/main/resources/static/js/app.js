import {categoryApi} from "./api/category-api.js";
import {productApi} from "./api/product-api.js";
import {userApi} from "./api/user-api.js";

const createCategoryForm = () => ({id: "", name: ""});
const createProductForm = () => ({id: "", name: "", price: "", date: "", categoryId: ""});
const createUserForm = () => ({username: "", password: "", fullname: "", enabled: true, role: "USER"});

const {createApp} = Vue;

createApp({
    data() {
        return {
            activeTab: "categories",
            errorMessage: "",
            successMessage: "",
            categories: [],
            products: [],
            users: [],
            categoryForm: createCategoryForm(),
            productForm: createProductForm(),
            userForm: createUserForm(),
            editingCategory: false,
            editingProduct: false,
            editingUser: false
        };
    },
    async mounted() {
        await this.loadAllData();
    },
    methods: {
        clearMessages() {
            this.errorMessage = "";
            this.successMessage = "";
        },
        showSuccess(message) {
            this.errorMessage = "";
            this.successMessage = message;
        },
        showError(error) {
            const apiMessage = error?.response?.data?.message;
            this.successMessage = "";
            this.errorMessage = apiMessage || "Unexpected error. Please try again.";
        },
        async loadAllData() {
            this.clearMessages();
            try {
                // Load all tables in one request batch for faster initial UI render.
                const [categories, products, users] = await Promise.all([
                    categoryApi.getAll(),
                    productApi.getAll(),
                    userApi.getAll()
                ]);
                this.categories = categories.sort((a, b) => a.id.localeCompare(b.id));
                this.products = products.sort((a, b) => a.id.localeCompare(b.id));
                this.users = users.sort((a, b) => a.username.localeCompare(b.username));
            } catch (error) {
                this.showError(error);
            }
        },

        // ---------- Bai 1: Category ----------
        resetCategoryForm() {
            this.categoryForm = createCategoryForm();
            this.editingCategory = false;
        },
        editCategory(category) {
            this.clearMessages();
            this.categoryForm = {...category};
            this.editingCategory = true;
        },
        async saveCategory() {
            this.clearMessages();
            try {
                if (this.editingCategory) {
                    await categoryApi.update(this.categoryForm.id, this.categoryForm);
                    this.showSuccess("Updated category successfully.");
                } else {
                    await categoryApi.create(this.categoryForm);
                    this.showSuccess("Created category successfully.");
                }
                this.resetCategoryForm();
                await this.loadAllData();
            } catch (error) {
                this.showError(error);
            }
        },
        async deleteCategory(id) {
            if (!confirm(`Delete category ${id}?`)) {
                return;
            }
            this.clearMessages();
            try {
                await categoryApi.remove(id);
                this.showSuccess("Deleted category successfully.");
                await this.loadAllData();
            } catch (error) {
                this.showError(error);
            }
        },

        // ---------- Bai 2: Product ----------
        resetProductForm() {
            this.productForm = createProductForm();
            this.editingProduct = false;
        },
        editProduct(product) {
            this.clearMessages();
            this.productForm = {
                id: product.id,
                name: product.name,
                price: product.price,
                date: product.date,
                categoryId: product.categoryId
            };
            this.editingProduct = true;
        },
        async saveProduct() {
            this.clearMessages();
            // Ensure price is sent as number to backend BigDecimal.
            const payload = {
                ...this.productForm,
                price: Number(this.productForm.price)
            };
            try {
                if (this.editingProduct) {
                    await productApi.update(this.productForm.id, payload);
                    this.showSuccess("Updated product successfully.");
                } else {
                    await productApi.create(payload);
                    this.showSuccess("Created product successfully.");
                }
                this.resetProductForm();
                await this.loadAllData();
            } catch (error) {
                this.showError(error);
            }
        },
        async deleteProduct(id) {
            if (!confirm(`Delete product ${id}?`)) {
                return;
            }
            this.clearMessages();
            try {
                await productApi.remove(id);
                this.showSuccess("Deleted product successfully.");
                await this.loadAllData();
            } catch (error) {
                this.showError(error);
            }
        },

        // ---------- Bai 3: User ----------
        resetUserForm() {
            this.userForm = createUserForm();
            this.editingUser = false;
        },
        editUser(user) {
            this.clearMessages();
            this.userForm = {
                username: user.username,
                password: user.password,
                fullname: user.fullname,
                enabled: user.enabled,
                role: user.role
            };
            this.editingUser = true;
        },
        async saveUser() {
            this.clearMessages();
            const payload = {
                ...this.userForm,
                role: this.userForm.role.toUpperCase()
            };
            try {
                if (this.editingUser) {
                    await userApi.update(this.userForm.username, payload);
                    this.showSuccess("Updated user successfully.");
                } else {
                    await userApi.create(payload);
                    this.showSuccess("Created user successfully.");
                }
                this.resetUserForm();
                await this.loadAllData();
            } catch (error) {
                this.showError(error);
            }
        },
        async deleteUser(username) {
            if (!confirm(`Delete user ${username}?`)) {
                return;
            }
            this.clearMessages();
            try {
                await userApi.remove(username);
                this.showSuccess("Deleted user successfully.");
                await this.loadAllData();
            } catch (error) {
                this.showError(error);
            }
        }
    }
}).mount("#app");
