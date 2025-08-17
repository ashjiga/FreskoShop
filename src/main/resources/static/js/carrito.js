<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.add-to-cart-form').forEach(form => {
            form.addEventListener('submit', function (event) {
                const button = form.querySelector('.add-to-cart-btn');
                const span = button.querySelector('.btn-text');

                button.disabled = true;
                span.textContent = 'Añadido al carrito!';

                button.classList.add('added-to-cart');
            });
        });
    });
</script>

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.add-to-cart-form').forEach(form => {
        form.addEventListener('submit', function (event) {
            const button = form.querySelector('.add-to-cart-btn');
            const span = button.querySelector('.btn-text');

            button.disabled = true;
            span.textContent = 'Añadido al carrito!';

            button.classList.add('added-to-cart');
        });
    });
});
