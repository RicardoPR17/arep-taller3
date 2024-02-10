function getMovieData() {
  let nameVar = document.getElementById("title").value;
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () {
    document.getElementById("movieData").innerHTML = this.responseText;
  };
  xhttp.open("GET", "/movie?movie=" + nameVar);
  xhttp.send();
  document.getElementById("title").value = "";
}
