function getMovieData() {
  let nameVar = document.getElementById("title").value;
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () {
    document.getElementById("movieData").innerHTML = JSON.stringify(JSON.parse(this.responseText), null, <br></br>);
  };
  xhttp.open("GET", "/movie?movie=" + nameVar);
  xhttp.send();
  document.getElementById("title").value = "";
}
