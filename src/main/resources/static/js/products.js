async function loadProducts() {
    const category = document.getElementById('categoryFilter').value.trim();
    const query = category ? `?category=${encodeURIComponent(category)}` : '';
    const products = await apiFetch(`/api/products${query}`);
    const me = await apiFetch('/api/auth/me');

    document.getElementById('productBody').innerHTML = products.map(product => `
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td>${product.quantity}</td>
            <td>$${product.price}</td>
            <td class="${product.lowStock ? 'low-stock' : ''}">${product.lowStock ? 'Low Stock' : 'OK'}</td>
            <td>
                <a href="/product-form.html?id=${product.id}">Edit</a>
                ${me.role === 'ROLE_ADMIN' ? `<button onclick="deleteProduct(${product.id})">Delete</button>` : ''}
            </td>
        </tr>
    `).join('');

    const report = await apiFetch(`/api/reports/inventory${query}`);
    document.getElementById('reportSummary').innerHTML = `
        <strong>Total Items:</strong> ${report.totalItems} | 
        <strong>Total Value:</strong> $${report.totalValue}
    `;
}

async function deleteProduct(id) {
    if (!confirm('Delete this product?')) return;
    try {
        await apiFetch(`/api/products/${id}`, { method: 'DELETE' });
        alert('Product deleted');
        loadProducts();
    } catch (error) {
        alert(error.message);
    }
}

loadProducts().catch(console.error);
