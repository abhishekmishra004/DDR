<?php
	
	class DbOperations
	{
		private $con;

		function __construct()
		{
			require_once dirname(__FILE__).'/DbConnect.php';
			$db = new DbConnect();

			$this->con = $db->connect();
		}

		function createUser($userid, $name , $email , $pass , $regd_no)
		{	
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `usertable` (`userid`, `name`, `email_id`, `password`, `regd_no`, `usertype`) VALUES ( '',?, ?, ?, ?, ?, 'student');");
				$stmt->bind_param("sssss",$userid,$name,$email,$password,$regd_no);
				if($stmt->execute())
				{
					return true;
				}
				else
				{
					return false;
				}
		
		}

		public function userLogin($userid,$pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("
				SELECT `userid` FROM `usertable` WHERE `userid` = ? AND `password` = ?");
			$stmt->bind_param("ss",$userid,$password);
			#echo $userid."\n".$pass."\n".$password."\n";
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		public function getUserbyUserid($userid){
			$stmt = $this->con->prepare ("SELECT * FROM usertable WHERE userid = ?");
			$stmt->bind_param("s",$userid);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}

		function isuserexist($userid)
		{
			$stmt = $this->con->prepare("SELECT * FROM `usertable` WHERE `userid` = ?");
			$stmt->bind_param("s",$userid);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0 ; 
		}
		function isemailexist($email)
		{
			$stmt = $this->con->prepare("SELECT * FROM `usertable` WHERE `email_id` = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0 ; 
		}

		function addnote($userid,$subject,$description,$semester,$title,$name,$type)
		{
				$stmt = $this->con->prepare("INSERT INTO `notes_table` ( `id`, `userid`, `subject`, `description`, `semester`, `title`, `name` ,`type`) VALUES ('', ?, ?, ?, ?, ?, ?,?);");
				$stmt->bind_param("sssssss",$userid,$subject,$description,$semester,$title,$name,$type);
				if($stmt->execute())
				{
					return true;
				}
				else
				{
					return false;
				}
		}


		function addproject($userid,$project_type,$projectSem,$title,$description,$technology,$name,$type)
		{
				$stmt = $this->con->prepare("INSERT INTO `project_table` (`id`, `userid`, `project_type`, `projectSem`, `title`, `description`, `technology`, `name`, `type`) VALUES ('', ?, ?, ?, ?, ?, ?, ?, ?);");
				$stmt->bind_param("ssssssss",$userid,$project_type,$projectSem,$title,$description,$technology,$name,$type);
				if($stmt->execute())
				{
					return true;
				}
				else
				{
					return false;
				}
		}

		function addbook($userid, $title, $author, $semester,$subject, $description, $version, $pdf)
		{
			$stmt = $this->con->prepare("INSERT INTO `ebook` (`id`,`userid`, `title`, `author`, `semester`,`subject`, `description`, `version`, `pdf`) VALUES ('',?, ?, ?, ?, ?,?, ?, ?);");
			$stmt->bind_param("ssssssss",$userid, $title, $author, $semester,$subject, $description, $version, $pdf);
			if($stmt->execute())
				{
					return true;
				}
			else
				{
					return false;
				}
		}


		function addres($userid,$semester,$subject,$description,$feature,$title,$name)
		{
				$stmt = $this->con->prepare("INSERT INTO `resourctable` (`id`, `userid`, `semester`, `subject`, `description`, `feature`, `title`, `name`) VALUES ('', ?, ?, ?, ?, ?, ?, ?);");
				$stmt->bind_param("sssssss",$userid,$semester,$subject,$description,$feature,$title,$name);
				if($stmt->execute())
				{
					return true;
				}
				else
				{
					return false;
				}
		}

		function addass($userid,$subject,$semester,$description,$feature,$title,$name)
		{
				$stmt = $this->con->prepare("INSERT INTO `syllabus_assignment` (`id`, `userid`, `subject`, `semester`, `description`, `feature`, `title`, `name`) VALUES ('', ?, ?, ?, ?, ?, ?, ?);");
				$stmt->bind_param("sssssss",$userid,$subject,$semester,$description,$feature,$title,$name);
				if($stmt->execute())
				{
					return true;
				}
				else
				{
					return false;
				}
		}



		function viewnotesall()
		{
			$stmt = "SELECT * FROM notes_table";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}

		function viewprojectall()
		{
			$stmt = "SELECT * FROM project_table";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}
		
		function viewallbook()
		{
			$stmt = "SELECT * FROM ebook";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;

		}

		function viewsemnotes( $sem)
		{
			$stmt = "SELECT * FROM notes_table WHERE semester = '".$sem."'";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}

		function viewsembook( $sem)
		{
			$stmt = "SELECT * FROM ebook WHERE semester = '".$sem."'";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}

		function viewsemproject( $sem)
		{
			$stmt = "SELECT * FROM project_table WHERE projectSem = '".$sem."'";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}

		function viewsemres($sem)
		{
			$stmt = "SELECT * FROM resourctable WHERE semester = '".$sem."'";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}		

		function viewsemass($sem)
		{
			$stmt = "SELECT * FROM syllabus_assignment WHERE semester = '".$sem."'";
			$result = mysqli_query($this->con, $stmt);
			while($row = mysqli_fetch_assoc($result)) {
			    $data[] =  $row;
			}
			return $data;
		}

		function changepass($userid,$pass)
		{
			$password = md5($pass);
			$stmt =  "UPDATE usertable SET password = '".$password."'"." WHERE userid = '".$userid."'";
			if(mysqli_query($this->con, $stmt))
				return true;
			else 
				return false;
		}

	}