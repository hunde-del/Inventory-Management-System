const params = new URLSearchParams(window.location.search);
const productId = params.get('id');
const form = document.getElementById('productForm');
const message = document.getElementById('message');

if (productId) {
    document.getElementById('formTitle').textContent = 'Edit Product';
    apiFetch(`/api/products/${productId}`).then(product => {
        document.getElementById('productId').value = product.id;
        document.getElementById('name').value = product.name;
        document.getElementById('category').value = product.category;
        document.getElementById('quantity').value = product.quantity;
        document.getElementById('price').value = product.price;
    }).catch(err => message.textContent = err.message);
}

form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const payload = {
        name: document.getElementById('name').value,
        category: document.getElementById('category').value,
        quantity: Number(document.getElementById('quantity').value),
        price: Number(document.getElementById('price').value)
    };

    try {
        if (payload.quantity < 0) {
            throw new Error('Quantity cannot be negative');
        }
        if (productId) {
            await apiFetch(`/api/products/${productId}`, { method: 'PUT', body: JSON.stringify(payload) });
            message.textContent = 'Product updated successfully';
        } else {
            await apiFetch('/api/products', { method: 'POST', body: JSON.stringify(payload) });
            message.textContent = 'Product added successfully';
            form.reset();
        }
        message.style.color = 'green';
    } catch (error) {
        message.textContent = error.message;
        message.style.color = 'red';
    }
});
