// document.addEventListener("DOMContentLoaded", function() {
//   let percentageWithoutAnswer = document.getElementById("percentageWithoutAnswer");
//   let averageAnswers = document.getElementById("averageAnswers");
//   let maximumAnswers = document.getElementById("maximumAnswers");
//
//   percentageWithoutAnswer.textContent = /*[[${percentageWithoutAnswer}]]*/;
//   averageAnswers.textContent = /*[[${averageAnswers}]]*/;
//   maximumAnswers.textContent = /*[[${maximumAnswers}]]*/;
//
//   let answersDistribution = /*[[${answersDistribution}]]*/;
//   let distributionLabels = answersDistribution.map(data => data.answerCount);
//   let distributionData = answersDistribution.map(data => data.questionCount);
//
//   let ctx = document.getElementById("answersDistributionChart").getContext("2d");
//   new Chart(ctx, {
//     type: "bar",
//     data: {
//       labels: distributionLabels,
//       datasets: [{
//         label: "Number of Questions",
//         data: distributionData,
//         backgroundColor: "rgba(75, 192, 192, 0.2)",
//         borderColor: "rgba(75, 192, 192, 1)",
//         borderWidth: 1
//       }]
//     },
//     options: {
//       scales: {
//         y: {
//           beginAtZero: true,
//           precision: 0
//         }
//       }
//     }
//   });
// });
