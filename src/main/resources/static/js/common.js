async function apiFetch(url, options = {}) {
    const response = await fetch(url, {
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
        ...options
    });

    if (response.status === 401 || response.status === 403) {
        window.location.href = '/login.html';
        return;
    }

    if (response.status === 204) {
        return null;
    }

    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.error || data.message || 'Request failed');
    }
    return data;
}

async function logout() {
    await apiFetch('/api/auth/logout', { method: 'POST' });
    window.location.href = '/login.html';
}

document.getElementById('logoutBtn')?.addEventListener('click', logout);
