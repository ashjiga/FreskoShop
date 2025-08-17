document.addEventListener('DOMContentLoaded', function() {
    document.body.addEventListener('click', function(event) {
        const button = event.target.closest('.ajax-update-btn');
        
        if (button) {
            const endpoint = button.getAttribute('data-endpoint');
            const targetId = button.getAttribute('data-target');
            
            if (!endpoint || !targetId) {
                console.error('Atributos de datos faltantes en el botón AJAX:', button);
                return;
            }

            const data = new URLSearchParams();
            for (const key in button.dataset) {
                if (key !== 'endpoint' && key !== 'target') {
                    data.append(key, button.dataset[key]);
                }
            }

          
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

          
            const headers = {
                'Content-Type': 'application/x-www-form-urlencoded',
            };
            headers[csrfHeader] = csrfToken;
            
            fetch(endpoint, {
                method: 'POST',
                headers: headers,
                body: data
            })
            
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const nuevoContenido = doc.querySelector(`#${targetId}`).innerHTML;
                document.getElementById(targetId).innerHTML = nuevoContenido;
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    });
});

