import axios from "axios";

const API_URL = "http://localhost:8080";

export const getExpenses = () => {
  return axios.get(`${API_URL}/expenses`);
};
export const createExpense = (expenseData) => {
  return axios.post(`${API_URL}/expenses`, expenseData);
};
export const deleteExpense = (id) => {
  return axios.delete(`${API_URL}/expenses/${id}`);
};