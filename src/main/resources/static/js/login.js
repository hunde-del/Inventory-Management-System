const form = document.getElementById('loginForm');
const message = document.getElementById('message');

form.addEventListener('submit', async (event) => {
    event.preventDefault();
    try {
        const data = await fetch('/api/auth/login', {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                username: document.getElementById('username').value,
                password: document.getElementById('password').value
            })
        });

        const payload = await data.json();
        if (!data.ok) {
            throw new Error(payload.error || 'Invalid credentials');
        }

        message.textContent = 'Login successful! Redirecting...';
        window.location.href = '/dashboard.html';
    } catch (error) {
        message.textContent = error.message;
        message.style.color = 'red';
    }
});
