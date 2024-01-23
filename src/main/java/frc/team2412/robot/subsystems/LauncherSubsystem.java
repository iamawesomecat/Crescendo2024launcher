package frc.team2412.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2412.robot.Hardware;

public class LauncherSubsystem extends SubsystemBase {
	// CONSTANTS
	// MOTOR SPEED VALUES
	public static final double SPEAKER_SHOOT_SPEED = 0.8;
	public static final double AMP_SHOOT_SPEED = 0.2;
	public static final double ANGLE_CHANGE_SPEED = 0.15;

	// HARDWARE
	private TalonFX launcherTopMotor;
	private TalonFX launcherBottomMotor;
	private CANSparkMax launcherAngleMotor;
	private CANSparkMax launcherHoodMotor;
	private SparkAbsoluteEncoder launcherAngleEncoder;
	private SparkAbsoluteEncoder launcherHoodEncoder;
	private SparkPIDController launcherAnglePidController;
	private SparkPIDController launcherHoodPidController;
	// Constructor
	public LauncherSubsystem() {

		// MOTOR INSTANCE VARIBLES
		launcherTopMotor = new TalonFX(Hardware.LAUNCHER_TOP_MOTOR_ID);
		launcherBottomMotor = new TalonFX(Hardware.LAUNCHER_BOTTOM_MOTOR_ID);
		launcherAngleMotor = new CANSparkMax(Hardware.LAUNCHER_ANGLE_MOTOR_ID, MotorType.kBrushless);
		launcherHoodMotor = new CANSparkMax(Hardware.LAUNCHER_HOOD_MOTOR_ID, MotorType.kBrushless);
		launcherAngleEncoder =
				launcherAngleMotor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
		launcherHoodEncoder = launcherHoodMotor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
		launcherAnglePidController = launcherAngleMotor.getPIDController();
		launcherHoodPidController = launcherHoodMotor.getPIDController();
	}

	// drive specific motor method
	public void driveMotor(TalonFX motor, double speed) {
		motor.set(speed);
	}

	// stop specific motor method
	public void stopMotor(TalonFX motor) {
		motor.stopMotor();
	}

	public void shoot(double speed) {
		launcherTopMotor.set(speed);
		launcherBottomMotor.set(-speed);
	}
	// returns the degrees of the angle of the launcher
	public double getAngle() {
		// get position returns a double in the form of rotations
		return Units.rotationsToDegrees(launcherAngleEncoder.getPosition());
	}

	public void setAngle(double angle) {
		launcherAnglePidController.setReference(Units.degreesToRotations(angle), ControlType.kPosition);
	}

	public double getHoodAngle() {
		return Units.rotationsToDegrees(launcherHoodEncoder.getPosition());
	}

	public void setHoodAngle(double angle) {
		launcherHoodPidController.setReference(Units.degreesToRotations(angle), ControlType.kPosition);
	}
}