document.addEventListener('DOMContentLoaded', function () {
    // Handle next and previous steps
    document.querySelector('.next-btn').addEventListener('click', function() {
        changeStep(1);
    });

    document.querySelector('.prev-btn').addEventListener('click', function() {
        changeStep(-1);
    });

    // Handle Save button
    document.querySelector('.save-btn').addEventListener('click', function() {
        saveClaim();
    });

    // Handle Submit button
    document.querySelector('.submit-btn').addEventListener('click', function() {
        submitClaim();
    });

    // Handle other buttons
    document.querySelector('.approve-btn').addEventListener('click', function() {
        approveClaim();
    });

    document.querySelector('.reject-btn').addEventListener('click', function() {
        rejectClaim();
    });

    document.querySelector('.return-btn').addEventListener('click', function() {
        returnClaim();
    });

    document.querySelector('.print-btn').addEventListener('click', function() {
        printClaim();
    });

    document.querySelector('.cancel-request-btn').addEventListener('click', function() {
        cancelRequest();
    });

    document.querySelector('.cancel-btn').addEventListener('click', function() {
        cancelClaim();
    });

    document.querySelector('.close-btn').addEventListener('click', function() {
        closeClaim();
    });

    // Function to handle step changes
    function changeStep(stepDelta) {
        const currentStep = document.querySelector('.form-step.active');
        const currentStepNumber = parseInt(currentStep.dataset.step);
        const nextStepNumber = currentStepNumber + stepDelta;
        const nextStep = document.querySelector(`.form-step[data-step="${nextStepNumber}"]`);

        if (nextStep) {
            currentStep.classList.remove('active');
            nextStep.classList.add('active');
            document.querySelector('.step.active').classList.remove('active');
            document.querySelector(`.step[data-step="${nextStepNumber}"]`).classList.add('active');
        }
    }

    // Function to save claim (uses AJAX to avoid full page reload)
    function saveClaim() {
        const form = document.querySelector('form');
        const formData = new FormData(form);

        fetch('/claims/save', {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                // Handle response from server
                console.log('Save response:', data);
            })
            .catch(error => {
                console.error('Error saving claim:', error);
            });
    }

    // Function to submit claim (sends the form)
    function submitClaim() {
        document.querySelector('form').submit();
    }

    // Dummy implementations for other actions
    function approveClaim() {
        alert('Claim approved.');
    }

    function rejectClaim() {
        alert('Claim rejected.');
    }

    function returnClaim() {
        alert('Claim returned.');
    }

    function printClaim() {
        window.print();
    }

    function cancelRequest() {
        alert('Request cancelled.');
    }

    function cancelClaim() {
        alert('Claim cancelled.');
    }

    function closeClaim() {
        alert('Claim closed.');
    }
});
