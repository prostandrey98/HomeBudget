// Для "Финансы" - скрытие/показ баланса
const toggleBalanceButton = document.querySelector('#toggle-balance');
const balance = document.querySelector('#balance');
const balanceAfterPayments = document.querySelector('#balance-after-payments');

if (toggleBalanceButton && balance && balanceAfterPayments) {
    toggleBalanceButton.addEventListener('click', function() {
        if (balance.textContent === '******') {
            balance.textContent = '0,00';
            balanceAfterPayments.textContent = '0,00';
            toggleBalanceButton.innerHTML = '<i class="fas fa-eye"></i>';
        } else {
            balance.textContent = '******';
            balanceAfterPayments.textContent = '******';
            toggleBalanceButton.innerHTML = '<i class="fas fa-eye-slash"></i>';
        }
    });
}

// Для "Финансы" - показ/скрытие формы и основного содержимого
if (window.location.pathname === '/' || window.location.pathname === '/finances') {
    const addButton = document.querySelector('.add-button');
    const transactionForm = document.querySelector('#transaction-form');
    const cancelButton = document.querySelector('#cancel-button');
    const mainContent = document.querySelector('#main-content');

    if (addButton && transactionForm && cancelButton && mainContent) {
        addButton.addEventListener('click', function() {
            mainContent.style.display = 'none';
            transactionForm.style.display = 'block';
            addButton.style.display = 'none';
        });

        cancelButton.addEventListener('click', function() {
            mainContent.style.display = 'block';
            transactionForm.style.display = 'none';
            addButton.style.display = 'block';
        });

        transactionForm.querySelector('form').addEventListener('submit', function() {
            mainContent.style.display = 'block';
            transactionForm.style.display = 'none';
            addButton.style.display = 'block';
        });
    }
}

// Для "Будущие расходы" - показ/скрытие формы
if (window.location.pathname === '/future-expenses') {
    const addButton = document.querySelector('.add-button');
    const futureExpenseForm = document.querySelector('#future-expense-form');
    const futureExpenseCancelButton = document.querySelector('#future-expense-form #cancel-button');
    const futureExpenseMainContent = document.querySelector('#main-content');

    if (addButton && futureExpenseForm && futureExpenseCancelButton && futureExpenseMainContent) {
        // Убедимся, что содержимое видно при загрузке страницы
        futureExpenseMainContent.style.display = 'block';
        futureExpenseForm.style.display = 'none';
        addButton.style.display = 'block';

        addButton.addEventListener('click', function() {
            futureExpenseMainContent.style.display = 'none';
            futureExpenseForm.style.display = 'block';
            addButton.style.display = 'none';
        });

        futureExpenseCancelButton.addEventListener('click', function() {
            futureExpenseMainContent.style.display = 'block';
            futureExpenseForm.style.display = 'none';
            addButton.style.display = 'block';
        });

        futureExpenseForm.querySelector('form').addEventListener('submit', function() {
            futureExpenseMainContent.style.display = 'block';
            futureExpenseForm.style.display = 'none';
            addButton.style.display = 'block';
        });
    }

    // Логика для чекбокса "Повторяющийся платеж"
    const recurringCheckbox = document.querySelector('#recurring-checkbox');
    const frequencySection = document.querySelector('#frequency-section');
    const frequencySelect = document.querySelector('#frequency-select');

    if (recurringCheckbox && frequencySection && frequencySelect) {
        recurringCheckbox.addEventListener('change', function() {
            if (this.checked) {
                frequencySection.classList.remove('frequency-hidden');
                frequencySection.classList.add('frequency-visible');
                frequencySelect.disabled = false; // Включаем select
            } else {
                frequencySection.classList.remove('frequency-visible');
                frequencySection.classList.add('frequency-hidden');
                frequencySelect.disabled = true; // Отключаем select
            }
        });
    }
}

// Для страницы редактирования
if (window.location.pathname.includes('/edit-future-expense')) {
    const editCancelButton = document.querySelector('#cancel-button');
    if (editCancelButton) {
        editCancelButton.addEventListener('click', function() {
            window.location.href = '/future-expenses';
        });
    }

    // Логика для чекбокса "Повторяющийся платеж"
    const recurringCheckbox = document.querySelector('#recurring-checkbox');
    const frequencySection = document.querySelector('#frequency-section');
    const frequencySelect = document.querySelector('#frequency-select');

    if (recurringCheckbox && frequencySection && frequencySelect) {
        recurringCheckbox.addEventListener('change', function() {
            if (this.checked) {
                frequencySection.classList.remove('frequency-hidden');
                frequencySection.classList.add('frequency-visible');
                frequencySelect.disabled = false; // Включаем select
            } else {
                frequencySection.classList.remove('frequency-visible');
                frequencySection.classList.add('frequency-hidden');
                frequencySelect.disabled = true; // Отключаем select
            }
        });
    }
}

// Для "Расходы" - временный alert
if (window.location.pathname === '/expenses') {
    document.querySelector('.add-button').addEventListener('click', function() {
        alert('Добавление нового расхода');
    });
}

// Обработка формы редактирования категорий (edit-categories.html)
const iconRadios = document.querySelectorAll('.icon-option input[type="radio"]');
// Визуальная обратная связь для выбора иконок
if (iconRadios) {
    iconRadios.forEach(radio => {
        radio.addEventListener('change', function() {
            const parent = radio.closest('.icon-grid');
            if (parent) {
                parent.querySelectorAll('.icon-option').forEach(option => {
                    option.classList.remove('selected');
                });
                radio.parentElement.classList.add('selected');
            }
        });
    });
}

// Управление темами
const themeToggle = document.querySelector('#theme-toggle');

const savedTheme = localStorage.getItem('theme') || 'light';
document.body.classList.add(savedTheme + '-theme');
if (themeToggle) {
    themeToggle.checked = savedTheme === 'dark';
}

if (themeToggle) {
    themeToggle.addEventListener('change', function() {
        const selectedTheme = this.checked ? 'dark' : 'light';
        document.body.classList.remove('light-theme', 'dark-theme');
        document.body.classList.add(selectedTheme + '-theme');
        localStorage.setItem('theme', selectedTheme);
    });
}

function handleCategorySelect(select, redirectUrl) {
    if (select) {
        console.log(`Found select: ${select.id}`); // Отладка
        select.addEventListener('change', function(e) {
            console.log(`Select changed: value=${this.value}, redirectUrl=${redirectUrl}}`); // Отладка
            if (this.value === 'edit-categories') {
                e.preventDefault(); // Предотвратить стандартное поведение
                select.removeAttribute('required'); // Отключить валидацию
                window.location.href = `/categories/edit?redirect=${encodeURIComponent(redirectUrl)}`;
                this.value = ''; // Сбросить выбор
                select.setAttribute('required', 'required'); // Восстановить валидацию
            }
        });
    } else {
        console.error(`Category select not found: ${select ? select.id : 'null'}`);
    }
}

// Для формы добавления
if (categorySelectAdd) {
    handleCategorySelect(categorySelectAdd, '/future-affairs');
} else {
    console.error('category-select-add not found');
}

// Для формы редактирования
if (editExpenseForm && categorySelectEdit) {
    const formAction = editExpenseForm.querySelector('form')?.action;
    const expenseId = formAction ? formAction.match(/\/edit-future\/expense\/(\d+)$/)?.[1] : '';
    if (expenseId) {
        handleCategorySelect(categorySelectEdit, `/edit-future-expense/${expenseId}`);
    } else {
        console.error('Expense ID not found in form action');
        handleCategorySelect(categorySelectEdit, '/future-affairs'); // Fallback
    }
} else {
    console.error('category-select-edit or editExpenseForm not found');
}

