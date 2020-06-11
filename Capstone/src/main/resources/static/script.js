project name = "Capstone (9)"

function temperatureConverter(valNum) {
  valNum = parseFloat(valNum);
  document.getElementById("outputFahrenheit").innerHTML=((valNum-273.15)*1.8)+32;
}
