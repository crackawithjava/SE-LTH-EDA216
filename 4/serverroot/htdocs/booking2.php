<?php
	require_once('database.inc.php');
	
	session_start();
	$db = $_SESSION['db'];
	$userId = $_SESSION['userId'];
	$movieId = $_SESSION['movieId'] = $_GET['movieId'];

	$db->openConnection();
	$performances = $db->getPerformancesForMovie($movieId);
	$movieName = $db->getMovieNameForMovie($movieId);
	$db->closeConnection();
?>

<html>
<head><title>Booking 2</title><head>
<body><h1>Booking 2</h1>
	<p>Current user: <?php print $userId ?></p>
	<p>Selected Movie: <?php $movieName ?></p>
	<p>
		Movies showing:
		<ul>
			<?php
				foreach ($performances as $performance) {
					print "<li><a href=\"booking3.php?performanceId=$performance[id]\" >$performance[date]</a></li>\n";
				}
			?>
		</ul>
	</p>
</body>
</html>
