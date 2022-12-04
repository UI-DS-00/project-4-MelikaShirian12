document.getElementById("sub").addEventListener("click", submit);


let inputNumbers;




function submit(e) {
    e.preventDefault();
    inputNumbers = document.getElementById("inputField").value;
    document.getElementById("answer").innerHTML = inputNumbers;
    console.log(inputNumbers);

    data = { input: inputNumbers };

    fetch('http://localhost:8080/word/special', {

        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then((response) =>
            response.json())
        .then((data) => {
            console.log('Success:', data);
            document.getElementById("answer").innerHTML = data.input;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}