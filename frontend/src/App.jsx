import { useState, useEffect } from "react";
import "./App.css";
import { getExpenses, createExpense, deleteExpense } from "./api/expenseApi";
function App() {
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");
  const [date, setDate] = useState("");
  const [expenses, setExpenses] = useState([]);

  useEffect(() => {
    console.log("Fetching expenses...");

    getExpenses()
      .then((response) => {
        console.log("Backend response:", response.data);
        setExpenses(response.data);
      })
      .catch((error) => {
        console.error("Error fetching expenses:", error);
      });
  }, []);

  const total = expenses.reduce((sum, expense) => {
    return sum + expense.amount;
  }, 0);

  const addExpense = () => {
    if (!description || !amount || !category || !date) {
      alert("Please fill in all fields");
      return;
    }

    if (!/[a-zA-Z]/.test(description)) {
      alert("Description should contain at least one letter");
      return;
    }

    if (Number(amount) <= 0) {
      alert("Amount must be greater than 0");
      return;
    }

    const newExpense = {
      description: description,
      amount: Number(amount),
      category: category,
      date: date,
      userId: 1,
    };

    createExpense(newExpense)
      .then((response) => {
        setExpenses([...expenses, response.data]);

        setDescription("");
        setAmount("");
        setCategory("");
        setDate("");
      })
      .catch((error) => {
        console.error("Error creating expense:", error);
        alert("Failed to add expense");
      });
  };
  const handleDelete = (id) => {
    deleteExpense(id)
      .then(() => {
        setExpenses(expenses.filter((expense) => expense.id !== id));
      })
      .catch((error) => {
        console.error("Error deleting expense:", error);
        alert("Failed to delete expense");
      });
  };
  return (
    <div className="app">
      <h1>ExpenseTrack</h1>

      <h2 className="total-expense">Total Expenses: ₹{total}</h2>

      <input
        type="text"
        placeholder="Enter expense description"
        value={description}
        onChange={(event) => setDescription(event.target.value)}
      />

      <input
        type="number"
        placeholder="Enter amount"
        value={amount}
        onChange={(event) => setAmount(event.target.value)}
      />

      <input
        type="text"
        placeholder="Enter category"
        value={category}
        onChange={(event) => setCategory(event.target.value)}
      />
      <input
        type="date"
        value={date}
        onChange={(event) => setDate(event.target.value)}
      />

      <button onClick={addExpense}>Add Expense</button>

      <h2>Expenses</h2>

      <div className="expense-table">
        <div className="table-header">
          <span>Description</span>
          <span>Category</span>
          <span>Date</span>
          <span>Amount</span>
        </div>

        {expenses.map((expense) => (
          <div className="table-row" key={expense.id}>
            <span>{expense.description}</span>
            <span>{expense.category}</span>
            <span>{expense.date}</span>
            <span>₹{expense.amount}</span>

            <button onClick={() => handleDelete(expense.id)}>Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
