async function loadDashboard() {
    const data = await apiFetch('/api/dashboard');
    if (!data) return;

    document.getElementById('stats').innerHTML = `
        <div><h4>Total Products</h4><p>${data.totalProducts}</p></div>
        <div><h4>Low Stock Items</h4><p>${data.lowStockItems}</p></div>
        <div><h4>Threshold</h4><p>&lt; 10 units</p></div>
    `;

    document.getElementById('recentBody').innerHTML = data.recentlyAddedProducts.map(p => `
        <tr>
            <td>${p.name}</td>
            <td>${p.category}</td>
            <td>${p.quantity}</td>
            <td>$${p.price}</td>
            <td>${new Date(p.createdAt).toLocaleString()}</td>
        </tr>
    `).join('');
}

loadDashboard().catch(console.error);
