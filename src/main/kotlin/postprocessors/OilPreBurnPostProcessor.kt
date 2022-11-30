package postprocessors

import com.conal.corda.StateAndRef
import com.conal.corda.StateAndRefPostProcessor
import com.conal.main.ContractState
import java.util.stream.Stream

class OilPreBurnPostProcessor : StateAndRefPostProcessor<OilPreBurnPostProcessor.PreBurnPojo> {
    companion object {
        const val POST_PROCESSOR_NAME = "oil-mining.OilPreBurnPostProcessor"
    }
    override val name: String
        get() = POST_PROCESSOR_NAME
    override val availableForRPC: Boolean
        get() = true
    override fun postProcess(inputs: Stream<StateAndRef<ContractState>>): Stream<PreBurnPojo> {
        return inputs.filter {
            it.state.data is OilState
        }.map {
            val state = it.state.data as OilState
            PreBurnPojo(state.linearId, "it.ref.txhash.toString()", 1)
        }
    }

    data class PreBurnPojo(
        val linearId: String,
        val txId: String,
        val index: Int
    )
}

class OilState(
    val linearId: String,
    val ref: StateAndRef<OilState>
) : ContractState {
    override val participants: String
        get() = ""
}