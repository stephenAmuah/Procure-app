
function openAddInventoryModal() {
    $('#addInventoryModal').modal('show');
}

function showErrorModal(message) {
    document.getElementById('errorMessage').innerText = message;
    $('#errorModal').modal('show');
}

function openUpdateModal(button) {
    const itemId = button.getAttribute('data-id');
    document.getElementById('updateItemId').value = itemId;
    const asset = document.getElementById('asset_' + itemId).innerText.trim();
    const brand = document.getElementById('brand_' + itemId).innerText.trim();
    const description = document.getElementById('description_' + itemId).innerText.trim();
    const maintenanceDate = document.getElementById('maintenanceDate_' + itemId).innerText.trim();
    const created = document.getElementById('created_' + itemId).innerText.trim();
    const status = document.getElementById('status_' + itemId).innerText.trim();
    document.getElementById('updateAsset').value = asset;
    document.getElementById('updateBrand').value = brand;
    document.getElementById('UpdateMaintenanceDate').value = maintenanceDate;
    document.getElementById('UpdateCreated').value = created;
    document.getElementById('updateDescription').value = description;
    document.getElementById('updateStatus').value = status;
    document.getElementById('updateItemForm').action = '/procureapp/items/update?id=' + itemId;
    $('#updateItemModal').modal('show');
}

async function deleteItem(button) {
    const itemId = button.getAttribute('data-id');
    try {
        const response = await fetch('/procureapp/items/delete?id=' + itemId, { method: 'POST' });
        if (response.ok) {
            window.location.reload();
        } else {
            if (response.status === 403) {
                showErrorModal('Only super admins are permitted to delete an item');
            } else {
                const errorText = await response.text();
                showErrorModal('Error occurred while processing the request: ' + errorText);
            }
        }
    } catch (error) {
        showErrorModal('Error occurred while processing the request: ' + error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('.dropdown-menu').addEventListener('click', function (event) {
        const target = event.target;
        if (target.matches('.dropdown-item')) {
            const filterType = target.innerText.trim();
            if (filterType === 'Date Purchased') {
                $('#filterDatePurchasedModal').modal('show');
            } else if (filterType === 'Maintenance Date') {
                $('#filterMaintenanceDateModal').modal('show');
            }
        }
    });

    document.getElementById('filterDatePurchasedForm').addEventListener('submit', function (e) {
        e.preventDefault();
        const startDate = document.getElementById('startDatePurchased').value;
        const endDate = document.getElementById('endDatePurchased').value;
        console.log('Filter by Date Purchased:', startDate, endDate);
        $('#filterDatePurchasedModal').modal('hide');
    });

    document.getElementById('filterMaintenanceDateForm').addEventListener('submit', function (e) {
        e.preventDefault();
        const startDate = document.getElementById('startDateMaintenance').value;
        const endDate = document.getElementById('endDateMaintenance').value;
        console.log('Filter by Maintenance Date:', startDate, endDate);
        $('#filterMaintenanceDateModal').modal('hide');
    });

    document.getElementById('filter').addEventListener('input', function () {
        const filterText = this.value.toLowerCase();
        document.querySelectorAll('#itemTable tr').forEach(row => {
            const asset = row.querySelector('td:nth-child(1)').innerText.toLowerCase();
            const typeOfAsset = row.querySelector('td:nth-child(3)').innerText.toLowerCase();
            row.style.display = (asset.includes(filterText) || typeOfAsset.includes(filterText)) ? '' : 'none';
        });
    });

    document.querySelectorAll('.ajax-form').forEach(form => {
        form.addEventListener('submit', async function (e) {
            e.preventDefault();
            const formData = new FormData(this);
            try {
                const response = await fetch(this.action, {
                    method: 'POST',
                    body: formData
                });
                if (response.ok) {
                    $('.modal').modal('hide');
                    window.location.reload();
                } else {
                    const errorText = await response.text();
                    console.error(errorText);
                    alert('Error occurred while processing the request: ' + errorText);
                }
            } catch (error) {
                console.error(error);
                alert('Error occurred while processing the request: ' + error);
            }
        });
    });
});
