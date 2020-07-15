<?php
	
	class Dbconnect
	{
		private $con;
		function __construct()
		{ }

		function connect()
		{
			include_once dirname(__FILE__).'/constraints.php';
			 $this->con = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

			 if(mysqli_connect_error())
			 {
			 	echo "Failed to connect to databse".mysqli_connect_err();
			 }

			return $this->con;
		}
	}