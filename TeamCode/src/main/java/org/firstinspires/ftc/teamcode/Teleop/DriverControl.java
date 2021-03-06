package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@TeleOp
public class DriverControl extends LinearOpMode
{
    //this is initializing ex)int number = DcMotor Motor
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;

    private BNO055IMU imu;
    private Orientation angles;


    //frontLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    @Override
    public void runOpMode() throws InterruptedException {
        //sets Motor equal to "motorOne" which is announce on the robot
        rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        helpDrive carl = new helpDrive(leftBackMotor, rightBackMotor, leftFrontMotor,rightFrontMotor);

       BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();//new parameters opbejct
       parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;//sertting parameter to degrees
        imu = hardwareMap.get(BNO055IMU.class,"imu");//getting from hardware map
       parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        imu.initialize(parameters);

        Gyro spinyboi = new Gyro(imu,angles,0.026,0,0, carl);

        waitForStart();

        while (opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("angle: ",angles.firstAngle);
            telemetry.update();

            double speed = 0.8;
            if(gamepad1.dpad_left){
                carl.left(speed);
            }else if(gamepad1.dpad_down){
                carl.forward(speed);
            }else if(gamepad1.dpad_right){
                carl.right(speed);
            }else if(gamepad1.dpad_up){
                carl.backward(speed);
            }else if(gamepad1.right_bumper){
                carl.turnRight(speed);
            }else if(gamepad1.left_bumper){
                carl.turnLeft(speed);
            }else if(gamepad1.left_trigger!=0) {
                carl.backward((0.5));
            }else if(gamepad1.right_trigger !=0) {
                carl.forward((0.5));
            }else if(gamepad1.a) {
                        spinyboi.rotate(0);
            }else{
                    rightBackMotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)/2);
                    rightFrontMotor.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x +gamepad1.right_stick_x)/2);
                    leftBackMotor.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x-gamepad1.right_stick_x)/2);
                    leftFrontMotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x-gamepad1.right_stick_x)/2);}
            }
            }
        }



