package info.tongrenlu.constants;

import org.apache.commons.lang3.SystemUtils;

public class ConstantsFactoryBean extends ConstantsFactory {

    public String getInputPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.getInputPathWindows() : this.getInputPathLinux();
    }

    public String getOutputPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.getOutputPathWindows() : this.getOutputPathLinux();
    }

    public String getConvertPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.getConvertPathWindows() : this.getConvertPathLinux();
    }
}
